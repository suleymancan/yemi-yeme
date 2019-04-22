let bgPage = chrome.extension.getBackgroundPage();
let word = bgPage.word;
let secilen = document.getElementById('secilen').innerHTML = word;
let url = "http://localhost:8080/api/yemi-yeme?source="+word;

let xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function(){
    if(this.readyState == 4 && this.status == 200){
        document.getElementById('sonuc').innerHTML = this.responseText;
    }
};
xhttp.open("GET",url, true);
xhttp.send();
