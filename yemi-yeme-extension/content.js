console.log('content');

window.addEventListener('mouseup',wordSelected);



function wordSelected(){
    //burada a taginin textlerini otomatik sec.
    let selectedText = window.getSelection().toString().trim();
    console.log(selectedText);
    if(selectedText.length > 0){
        let message = {
            text: selectedText
        }
        chrome.runtime.sendMessage(message);
    }
}

