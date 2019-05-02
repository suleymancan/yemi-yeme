(function () {

  let oldLength = -1;
  

  const regexAnySocialMediaURL = '(^(http|https):\\/\\/)?(?:www\\.|open)?(instagram|facebook|twitter|youtube|youtu|soundcloud|spotify|t|tumblr|github|gitlab|medium)\\.(com|be|co)\\/.*';

  let newTweetHomeButton = document.getElementsByClassName('js-nav js-tooltip js-dynamic-tooltip')[0];
  newTweetHomeButton.onclick = function () {
    oldYemiYemeClear();
    pageLoad();
  };


  let oldTweetListLength = document.getElementsByClassName('TweetTextSize  js-tweet-text tweet-text').length;
  let newTweetClick = true;
  listen(window.history.length);


  // https://stackoverflow.com/a/49772691
  function listen(currentLength) {

    let tweetListLength = document.getElementsByClassName('TweetTextSize  js-tweet-text tweet-text').length;

    let newTweet = document.getElementsByClassName('new-tweets-bar js-new-tweets-bar')[0];


    if (tweetListLength > oldTweetListLength) {
      setTweetListLength(tweetListLength);
    }

    if (newTweetClick && newTweet !== undefined) {
      newTweetClick = false;
      newTweet.onclick = function () {
        oldYemiYemeClear();
        pageLoad();
      };
    }

    if (currentLength !== oldLength) {
      oldYemiYemeClear();
      pageLoad();
    }

    oldLength = window.history.length;
    setTimeout(function () {
      listen(window.history.length);
    }, 1000);

  }


//tweetlerin tamaminin yenilenmesi yerine degisen kismin yenilenmesi saglanabilir.
  function setTweetListLength(newTweetListLength) {
    oldTweetListLength = newTweetListLength;
    oldYemiYemeClear();
    pageLoad();
  }


  function oldYemiYemeClear() {
    const elements = document.getElementsByClassName('yemi-yeme');
    while (elements.length > 0) elements[0].remove();
  }

  function pageLoad() {


    let tweetList = document.getElementsByClassName('TweetTextSize  js-tweet-text tweet-text');

    let filterTweetList = doFilterTweetList(tweetList);

    for (let tweet in filterTweetList) {

      let selectedText = selectedTextPreProcess(filterTweetList[tweet].innerText);

      if (selectedText && selectedText.length > 0) {
        let xhttp = new XMLHttpRequest();
        let url = "http://localhost:8080/api/yemi-yeme?source=" + selectedText;

        xhttp.onreadystatechange = function () {

          if (this.readyState === 4 && this.status === 200) {
            filterTweetList[tweet].appendChild(createChildElement(this.responseText));

          }

        };
        xhttp.open("GET", url, true);
        xhttp.send();

      }
    }


  }

  function doFilterTweetList(tweetList) {
    let filterTweetList = [];
    for (let tweet in tweetList) {
      if (isTweetLangEqualsTR(tweetList[tweet]) && isTweetChildElementNews(tweetList[tweet])) {
        filterTweetList[tweet] = tweetList[tweet];
      }
    }
    return filterTweetList;

  }

  function isTweetLangEqualsTR(tweet) {
    return tweet.lang === 'tr';
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
    return child.tagName === 'A';
  }


  function isChildTweetHasAttributeAndAttributeNews(child) {
    return child.hasAttribute('data-expanded-url') && isAttributeNews(child.getAttribute('data-expanded-url'));
  }


// youtube, insta, face  vs. kontrol edilmeli mi?
  function isAttributeNews(attribute) {
    return !attribute.match(regexAnySocialMediaURL);
  }

  function selectedTextPreProcess(selectedText) {
    let removedLink = selectedText.split('http')[0].trim();
    return removedLink.replace(/[\[\]▼|]/g, "");

  }


  function createChildElement(responseText) {
    let childElement = document.createElement('div');
    childElement.textContent = responseText;
    childElement.classList.add('yemi-yeme');

    if (responseText === 'CLICKBAIT') {
      childElement.style.color = "red";
      childElement.style.textAlign = "right";
    } else {
      childElement.style.color = "green";
      childElement.style.textAlign = "right";
    }
    return childElement;
  }
})();

