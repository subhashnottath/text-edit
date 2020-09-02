function chooseFile() {
    document.getElementById("fileChoice").click();
}

function showFile(fileChoice) {
    fileLabel = document.getElementById("fileLabel");
    editor = document.getElementById("editor");
    fileLabel.innerHTML = "  Currently editing : " + fileChoice.files[0].name;
    let file = fileChoice.files[0];
    let reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function() {
        editor.value = reader.result;
    };
    reader.onerror = function() {
        editor.innerHTML = reader.error;
    };
}

function process() {
    const url = '/process';
    (async () => {
        const editorWidth = document.getElementById("pageWidth").value;
        const editorContent = document.getElementById("editor").value;
        const format = document.querySelector('input[name="format"]:checked').value;
        const align = document.querySelector('input[name="align"]:checked').value;
        const rawResponse = await fetch(url, {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
            editorWidth: editorWidth,
            editorContent: editorContent,
            format: format,
            align: align,
            })
        });
      const response = await rawResponse.text();
      document.getElementById("editor").value = response;
    })();
}

function download() {
    let text = document.getElementById("editor").value;
//    text = text.replace(/\n/g, "\r\n"); // To retain the Line breaks.
    let blob = new Blob([text], { type: "text/plain"});
    let anchor = document.createElement("a");
    anchor.download = "download.txt";
    anchor.href = window.URL.createObjectURL(blob);
    anchor.target ="_blank";
    anchor.style.display = "none"; // just to be safe!
    document.body.appendChild(anchor);
    anchor.click();
    document.body.removeChild(anchor);
 }
