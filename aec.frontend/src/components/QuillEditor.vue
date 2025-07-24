<script setup>
import { defineProps, defineEmits, ref, onMounted, watch, nextTick } from 'vue';
import Quill from 'quill';
import QuillBetterTable from 'quill-better-table';
import ImageResize from '@/libs/quill-image-resize/ImageResize';
import 'quill/dist/quill.snow.css';
import 'quill-better-table/dist/quill-better-table.css';

Quill.register(
    {
        'modules/better-table': QuillBetterTable,
        'modules/imageResize': ImageResize
    },
    true
);

const props = defineProps({ modelValue: String });
const emit = defineEmits(['update:modelValue']);
const editorRef = ref(null);
let quill = null;

const Font = Quill.import('formats/font');
Font.whitelist = ['sans-serif', 'serif', 'monospace', 'poppins', 'arial', 'times-new-roman', 'roboto', 'tahoma', 'verdana', 'open-sans'];
Quill.register(Font, true);

const Size = Quill.import('attributors/style/size');
Size.whitelist = ['12px', '14px', '16px', '18px', '24px', '32px'];
Quill.register(Size, true);

const convertSpacesToNbsp = (html) => {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');
    const walk = (node) => {
        node.childNodes.forEach((child) => {
            if (child.nodeType === Node.TEXT_NODE) {
                child.textContent = child.textContent.replace(/ {2,}/g, (match) => {
                    return ' ' + '\u00A0'.repeat(match.length - 1);
                });
            } else {
                walk(child);
            }
        });
    };
    walk(doc.body);
    return doc.body.innerHTML;
};

const insertTable = () => {
    const tableModule = quill.getModule('better-table');
    tableModule.insertTable(3, 3);
};

const adjustHeight = () => {
    nextTick(() => {
        const editorEl = editorRef.value?.querySelector('.ql-editor');
        if (editorEl) {
            editorEl.style.height = 'auto';
            editorEl.style.minHeight = '150px';
            editorEl.style.maxHeight = '100%';
            editorEl.style.overflowY = 'auto';
            editorEl.style.padding = '10px';
        }
    });
};

const alignStates = ['left', 'center', 'right'];
const getCurrentTableAlign = () => {
    if (!quill) return 'left';
    const selection = quill.getSelection();
    if (!selection) return 'left';
    let [blot] = quill.scroll.descendant(Quill.import('blots/block'), selection.index);
    while (blot && blot.domNode && blot.domNode.tagName !== 'TABLE') {
        blot = blot.parent;
    }
    if (blot && blot.domNode && blot.domNode.tagName === 'TABLE') {
        const ml = blot.domNode.style.marginLeft;
        const mr = blot.domNode.style.marginRight;
        if (ml === 'auto' && mr === 'auto') return 'center';
        if (ml === 'auto' && (mr === '0' || mr === '0px')) return 'right';
        return 'left';
    }
    return 'left';
};

const cycleAlignTable = () => {
    const alignNow = getCurrentTableAlign();
    const idx = alignStates.indexOf(alignNow);
    const nextAlign = alignStates[(idx + 1) % alignStates.length];
    alignTable(nextAlign);
};

const alignTable = (alignType) => {
    const selection = quill.getSelection();
    if (!selection) return;
    let [blot] = quill.scroll.descendant(Quill.import('blots/block'), selection.index);
    while (blot && blot.domNode && blot.domNode.tagName !== 'TABLE') {
        blot = blot.parent;
    }
    if (blot && blot.domNode && blot.domNode.tagName === 'TABLE') {
        if (alignType === 'center') {
            blot.domNode.style.marginLeft = 'auto';
            blot.domNode.style.marginRight = 'auto';
        } else if (alignType === 'right') {
            blot.domNode.style.marginLeft = 'auto';
            blot.domNode.style.marginRight = '0';
        } else if (alignType === 'left') {
            blot.domNode.style.marginLeft = '0';
            blot.domNode.style.marginRight = 'auto';
        }
    }
};

const initEditor = () => {
    if (!editorRef.value) return;

    quill = new Quill(editorRef.value, {
        theme: 'snow',
        modules: {
            toolbar: {
                container: '#quill-toolbar',
                handlers: {
                    table: () => insertTable()
                }
            },
            'better-table': {
                operationMenu: {
                    items: {
                        unmergeCells: { text: 'Unmerge cells' }
                    },
                    color: {
                        colors: ['red', 'green', 'yellow', 'blue', 'white'],
                        text: 'Background Colors:'
                    }
                }
            },
            imageResize: {
                modules: ['Resize', 'DisplaySize', 'Toolbar']
            },
            clipboard: { matchVisual: false },
            keyboard: {
                bindings: QuillBetterTable.keyboardBindings
            }
        },
        placeholder: 'Nhập nội dung trả lời tại đây.'
    });

    if (props.modelValue) {
        quill.clipboard.dangerouslyPasteHTML(props.modelValue);
    }

    quill.on('text-change', () => {
        const html = editorRef.value.querySelector('.ql-editor').innerHTML;
        const htmlWithNbsp = convertSpacesToNbsp(html);
        emit('update:modelValue', htmlWithNbsp);
    });

    //  Dán nhiều ảnh trong 1 dòng bằng Delta
    quill.root.addEventListener('paste', (e) => {
        const clipboardData = e.clipboardData || window.clipboardData;
        const items = clipboardData.items;
        let images = [];

        for (let i = 0; i < items.length; i++) {
            if (items[i].type.indexOf('image') !== -1) {
                const file = items[i].getAsFile();
                images.push(file);
                e.preventDefault(); // Ngăn paste mặc định
            }
        }

        if (images.length > 0) {
            let loaded = 0;
            let ops = [];

            images.forEach((file) => {
                const reader = new FileReader();
                reader.onload = (evt) => {
                    ops.push({
                        insert: { image: evt.target.result }
                    });
                    loaded++;
                    if (loaded === images.length) {
                        const range = quill.getSelection(true);
                        // Chèn tất cả ảnh vào cùng một vị trí
                        quill.updateContents(
                            {
                                ops: [{ retain: range.index }, ...ops]
                            },
                            'user'
                        );
                        // Đặt con trỏ sau ảnh cuối
                        quill.setSelection(range.index + ops.length, 0);
                    }
                };
                reader.readAsDataURL(file);
            });
        }
    });

    // Làm sạch định dạng Word
    quill.clipboard.addMatcher(Node.ELEMENT_NODE, (node, delta) => {
        if (node.nodeName === 'SPAN' || node.nodeName === 'FONT') {
            delta.ops.forEach((op) => {
                if (op.attributes) {
                    delete op.attributes.font;
                    delete op.attributes.size;
                }
            });
        }
        return delta;
    });

    // Đảm bảo ảnh được chèn inline
    quill.clipboard.addMatcher('IMG', (node) => {
        return {
            ops: [
                { insert: { image: node.getAttribute('src') } },
                { insert: '\u00A0' } // Thêm khoảng trắng không ngắt sau ảnh
            ]
        };
    });

    adjustHeight();
};

watch(
    () => props.modelValue,
    (newVal) => {
        if (quill && newVal !== quill.root.innerHTML) {
            quill.root.innerHTML = newVal || '';
        }
    }
);

onMounted(() => {
    initEditor();
});
</script>

<template>
    <div class="custom-quill-editor">
        <div id="quill-toolbar">
            <span class="ql-formats">
                <select class="ql-font">
                    <option value="sans-serif" selected>Sans Serif</option>
                    <option value="serif">Serif</option>
                    <option value="monospace">Monospace</option>
                </select>
                <select class="ql-size">
                    <option value="12px">12px</option>
                    <option value="14px" selected>14px</option>
                    <option value="16px">16px</option>
                    <option value="18px">18px</option>
                    <option value="24px">24px</option>
                    <option value="32px">32px</option>
                </select>
            </span>
            <span class="ql-formats">
                <button class="ql-bold" />
                <button class="ql-italic" />
                <button class="ql-underline" />
                <button class="ql-strike" />
            </span>
            <span class="ql-formats">
                <select class="ql-color" />
                <select class="ql-background" />
            </span>
            <span class="ql-formats">
                <button class="ql-blockquote" />
                <button class="ql-code-block" />
            </span>
            <span class="ql-formats">
                <select class="ql-header">
                    <option value="1" />
                    <option value="2" />
                    <option selected />
                </select>
                <select class="ql-align" />
            </span>
            <span class="ql-formats">
                <button class="ql-list" value="ordered" />
                <button class="ql-list" value="bullet" />
                <button class="ql-image" />
                <button class="ql-table" />
                <button type="button" @click="cycleAlignTable">
                    <i class="fas fa-table-cells-large" />
                </button>
            </span>
        </div>
        <div ref="editorRef" class="editor-container" />
    </div>
</template>

<style scoped>
.custom-quill-editor .ql-editor {
    font-family: inherit;
    font-size: 1rem;
}

:deep(.ql-editor) {
    word-wrap: break-word !important;
    white-space: normal !important;
    display: block !important;
    line-height: 1.6 !important;
}

:deep(.ql-editor p),
:deep(.ql-editor h1),
:deep(.ql-editor h2),
:deep(.ql-editor h3),
:deep(.ql-editor div) {
    display: block !important;
    margin: 0 !important;
    padding: 0 !important;
    line-height: 1.6 !important;
}

:deep(.ql-editor img) {
    display: inline-block !important;
    vertical-align: top !important;
    object-fit: contain !important;
    border-radius: 4px !important;
    float: none !important;
    clear: none !important;
    height: auto !important;
    margin: 2px !important;
    white-space: normal !important;
    word-break: normal !important;
}

:deep(.ql-editor .ql-align-left),
:deep(.ql-editor p.ql-align-left),
:deep(.ql-editor h1.ql-align-left),
:deep(.ql-editor h2.ql-align-left),
:deep(.ql-editor h3.ql-align-left),
:deep(.ql-editor div.ql-align-left) {
    text-align: left !important;
}

:deep(.ql-editor .ql-align-center),
:deep(.ql-editor p.ql-align-center),
:deep(.ql-editor h1.ql-align-center),
:deep(.ql-editor h2.ql-align-center),
:deep(.ql-editor h3.ql-align-center),
:deep(.ql-editor div.ql-align-center) {
    text-align: center !important;
}

:deep(.ql-editor .ql-align-right),
:deep(.ql-editor p.ql-align-right),
:deep(.ql-editor h1.ql-align-right),
:deep(.ql-editor h2.ql-align-right),
:deep(.ql-editor h3.ql-align-right),
:deep(.ql-editor div.ql-align-right) {
    text-align: right !important;
}

:deep(.ql-editor .ql-align-justify),
:deep(.ql-editor p.ql-align-justify),
:deep(.ql-editor h1.ql-align-justify),
:deep(.ql-editor h2.ql-align-justify),
:deep(.ql-editor h3.ql-align-justify),
:deep(.ql-editor div.ql-align-justify) {
    text-align: justify !important;
}

:deep(.ql-editor p:has(img)),
:deep(.ql-editor div:has(img)),
:deep(.ql-editor h1:has(img)),
:deep(.ql-editor h2:has(img)),
:deep(.ql-editor h3:has(img)) {
    display: block !important;
    width: 100% !important;
    white-space: normal !important;
    word-wrap: break-word !important;
    line-height: 1.2 !important;
    min-height: 1.2em !important;
}

/* Font map cho dropdown font */
.custom-quill-editor .ql-font-sans-serif {
    font-family: sans-serif;
}
.custom-quill-editor .ql-font-serif {
    font-family: serif;
}
.custom-quill-editor .ql-font-monospace {
    font-family: monospace;
}
.custom-quill-editor .ql-font-poppins {
    font-family: 'Poppins', sans-serif;
}
.custom-quill-editor .ql-font-arial {
    font-family: 'Arial', sans-serif;
}
.custom-quill-editor .ql-font-times-new-roman {
    font-family: 'Times New Roman', serif;
}
.custom-quill-editor .ql-font-roboto {
    font-family: 'Roboto', Arial, sans-serif;
}
.custom-quill-editor .ql-font-tahoma {
    font-family: 'Tahoma', Geneva, sans-serif;
}
.custom-quill-editor .ql-font-verdana {
    font-family: 'Verdana', Geneva, sans-serif;
}
.custom-quill-editor .ql-font-open-sans {
    font-family: 'Open Sans', Arial, sans-serif;
}

/* Align dropdown styles */
.custom-quill-editor .ql-align .ql-picker-label::before,
.custom-quill-editor .ql-align .ql-picker-item::before {
    content: 'Left';
}
.custom-quill-editor .ql-align .ql-picker-label[data-value='center']::before,
.custom-quill-editor .ql-align .ql-picker-item[data-value='center']::before {
    content: 'Center';
}
.custom-quill-editor .ql-align .ql-picker-label[data-value='right']::before,
.custom-quill-editor .ql-align .ql-picker-item[data-value='right']::before {
    content: 'Right';
}
.custom-quill-editor .ql-align .ql-picker-label[data-value='justify']::before,
.custom-quill-editor .ql-align .ql-picker-item[data-value='justify']::before {
    content: 'Justify';
}

.custom-quill-editor .ql-align .ql-picker-label {
    width: auto;
    min-width: 60px;
}
.custom-quill-editor .ql-align .ql-picker-options {
    min-width: 80px;
}
</style>
