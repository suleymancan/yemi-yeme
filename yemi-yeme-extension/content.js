

window.addEventListener('load',wordSelected);

function createChildElement(responseText){
    let childElement = document.createElement('div');
    childElement.textContent = responseText;

    if(responseText == 'CLICKBAIT'){
        childElement.style.color = "red";
        childElement.style.textAlign = "right";
    }
    else{
        childElement.style.color = "green";
        childElement.style.textAlign = "right";
    }
    return childElement;
}

function wordSelected(){



    //burada a taginin textlerini otomatik sec.
    let tweetList = document.getElementsByClassName('TweetTextSize  js-tweet-text tweet-text');

    for(let tweet in tweetList){
        let selectedText = tweetList[tweet].innerText;
        if(selectedText && selectedText.length > 0){
            let xhttp = new XMLHttpRequest();
            let url = "http://localhost:8080/api/yemi-yeme?source="+selectedText;

            xhttp.onreadystatechange = function(){

                if(this.readyState == 4 && this.status == 200){
                    tweetList[tweet].appendChild(createChildElement(this.responseText));
                }

            };
            xhttp.open("GET",url, true);
            xhttp.send();

        }
    }


}

