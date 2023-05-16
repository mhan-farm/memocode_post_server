import {basicSetup, EditorView} from "codemirror";
import {markdown} from "@codemirror/lang-markdown";
import {languages} from "@codemirror/language-data";

const codeMirrorEditor = (init, parent) => new EditorView({
    doc: init,
    extensions: [
        basicSetup,
        markdown({codeLanguages: languages}),
    ],
    parent: parent
});

export default codeMirrorEditor;