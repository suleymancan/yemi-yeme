//only works when twitter is first loaded.
window.addEventListener('load',pageLoad);

let oldLength = -1;
listen(window.history.length);
function listen(currentLength) {
  if (currentLength != oldLength) {
    const elements = document.getElementsByClassName('yemi-yeme');
    console.log(elements);
    while(elements.length>0) elements[0].remove();
    pageLoad();
  }

  oldLength = window.history.length;
  setTimeout(function () {
    listen(window.history.length);
  }, 1000);
}


// twitter is first loaded works twice :(
// ilk yuklenen twitte iki kere sonuc yaziyor.
function pageLoad() {

  console.log('page load calisti***************************')

  let tweetList = document.getElementsByClassName('TweetTextSize  js-tweet-text tweet-text');

  let filterTweetList = doFilterTweetList(tweetList);

  for (let tweet in filterTweetList) {

    let selectedText = selectedTextClearLink(filterTweetList[tweet].innerText);
    console.log(selectedText);
    if (selectedText && selectedText.length > 0) {
      let xhttp = new XMLHttpRequest();
      let url = "http://localhost:8080/api/yemi-yeme?source=" + selectedText;

      xhttp.onreadystatechange = function () {

        if (this.readyState == 4 && this.status == 200) {
          filterTweetList[tweet].appendChild(createChildElement(this.responseText));

        }

      };
      xhttp.open("GET", url, true);
      xhttp.send();

    }
  }



}

function doFilterTweetList(tweetList) {
  let filterTweetList = new Array();
  for (let tweet in tweetList) {
    if (isTweetLangEqualsTR(tweetList[tweet]) && isTweetChildElementNews(tweetList[tweet])) {
      filterTweetList[tweet] = tweetList[tweet];
    }
  }
  return filterTweetList;

}

function isTweetLangEqualsTR(tweet) {
  if (tweet.lang === 'tr') {
    return true;
  }
  return false;
}

function isTweetChildElementNews(tweet) {
  if (tweet.firstElementChild != null) {

    for (let child in tweet.children) {

      if (isChildTweetTagNameEqualsA(tweet.children[child]) && isChildTweetHasAttributeAndAttributeNews(tweet.children[child])) {
        return true;
      }
    }
  }
  return false;
}


function isChildTweetTagNameEqualsA(child) {
  if (child.tagName === 'A') {
    return true;
  }
  return false;
}


function isChildTweetHasAttributeAndAttributeNews(child) {
  if (child.hasAttribute('data-expanded-url') && isAttributeNews(child.getAttribute('data-expanded-url'))) {
    return true;
  }
  return false;
}


//check youtube vs.
function isAttributeNews(attribute) {
  if (attribute.match('^(http|https):\\/\\/twitter.com\\/.*')) {
    return false;
  }
  return true;
}

function selectedTextClearLink(selectedText) {
  return selectedText.split('http')[0].trim();
}


function createChildElement(responseText) {
  let childElement = document.createElement('div');
  childElement.textContent = responseText;
  childElement.classList.add('yemi-yeme');

  if (responseText == 'CLICKBAIT') {
    childElement.style.color = "red";
    childElement.style.textAlign = "right";
  } else {
    childElement.style.color = "green";
    childElement.style.textAlign = "right";
  }
  return childElement;
}

