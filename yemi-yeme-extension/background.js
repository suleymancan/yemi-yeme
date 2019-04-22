console.log('background running...');

chrome.runtime.onMessage.addListener(receiver);

 window.word = "";

function receiver(request, sender, sendResponse){
    console.log('background...');
    console.log(request);
    word = request.text;
}