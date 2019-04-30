(function () {

  const regexTwitterURLDomain = '(^(http|https):\\/\\/)?(?:www\\.)?twitter\\.com(\\/|$)$';

  const regexAnyTwitterURL = '(^(http|https):\\/\\/)?(?:www\\.)?twitter\\.com\\/.*';

  let homePageFirstLoad = false;

  let oldLength = -1;


// twitter ana sayfa tek seferde yuklenmedigi icin boyle bir seye ihtiyac duydum.
  if(window.location.href.match(regexTwitterURLDomain)){
    homePageFirstLoad = true;
    window.addEventListener('load', function () {

      oldYemiYemeClear();
      pageLoad();
    });
  }

listen(window.history.length);

  // https://stackoverflow.com/a/49772691
function listen(currentLength) {

  let newTweet = document.getElementsByClassName('new-tweets-bar js-new-tweets-bar')[0];
  let newTweetHomeButton =  document.getElementsByClassName('js-nav js-tooltip js-dynamic-tooltip')[0];
  // iyi bir yaklasim degil. yeni tweetlere ulasmam gerek.
  if(newTweet != undefined){
    newTweet.onclick = function () {

      location.reload();

    };
    if(window.location.href.match( regexTwitterURLDomain)){

      newTweetHomeButton.onclick = function () {
        location.reload();
      }
    }
  }


  if (currentLength != oldLength) {
    // twitter/user'dan twitter.com'a gelince, twitter ana sayfa tek seferde yuklenmedigi icin boyle bir seye ihtiyac duydum.
    if(!homePageFirstLoad && window.location.href.match(regexTwitterURLDomain)){
      location.reload();
      homePageFirstLoad = true;
    }
    else{
    oldYemiYemeClear();
    pageLoad();
    }
  }

  oldLength = window.history.length;
  setTimeout(function () {
    listen(window.history.length);
  }, 1000);

}





function oldYemiYemeClear() {
  const elements = document.getElementsByClassName('yemi-yeme');
  while(elements.length>0) elements[0].remove();
}

function pageLoad() {

  console.log('page load calisti***************************');

  let tweetList = document.getElementsByClassName('TweetTextSize  js-tweet-text tweet-text');
  console.log(tweetList[0]);
  let filterTweetList = doFilterTweetList(tweetList);
  console.log('filterTweetList length->>', filterTweetList.length);
  for (let tweet in filterTweetList) {

    let selectedText = selectedTextPreProcess(filterTweetList[tweet].innerText);
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


// youtube, insta, face  vs. kontrol edilmeli mi?
function isAttributeNews(attribute) {
  if (attribute.match(regexAnyTwitterURL)) {
    return false;
  }
  return true;
}

function selectedTextPreProcess(selectedText) {
  let removedLink = selectedText.split('http')[0].trim();
  return removedLink.replace(/[\[\]\▼\|]/g, "");

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
})();

