(function () {

  // listen metodu
  let oldLength = -1;

// sadece yeni yüklenen tweetlere sorgu yapılması için tutulan flag.
  let worked = false;

  const regexAnySocialMediaURL = '(^(http|https):\\/\\/)?(?:www\\.|open)?(instagram|facebook|twitter|youtube|youtu|soundcloud|spotify|t|tumblr|github|gitlab|medium|pscp|eksisozluk)\\.(com|be|co|tv)\\/.*';

  // yeni tweetler icin home butonuna basıldığında
  let newTweetHomeButton = document.getElementsByClassName('js-nav js-tooltip js-dynamic-tooltip')[0];
  newTweetHomeButton.onclick = function () {
    oldYemiYemeClear();
    pageLoad();
  };


  let oldTweetListLength = document.getElementsByClassName('TweetTextSize js-tweet-text tweet-text').length;
  let newTweetClick = true;
  listen(window.history.length);


// https://stackoverflow.com/a/49772691
  function listen(currentLength) {

    let tweetListLength = document.getElementsByClassName('TweetTextSize js-tweet-text tweet-text').length;

    let newTweet = document.getElementsByClassName('new-tweets-bar js-new-tweets-bar')[0];


    if (tweetListLength > oldTweetListLength) {
      setTweetListLength(tweetListLength);
    }

    if (newTweetClick && newTweet !== undefined) {
      newTweetClick = false;
      newTweet.onclick = function () {
        pageLoad();
      };
    }

    if (currentLength !== oldLength) {
      oldTweetListLength = 0;
      oldYemiYemeClear();
      pageLoad();
    }

    oldLength = window.history.length;
    setTimeout(function () {
      listen(window.history.length);
    }, 1000);

  }

  function oldYemiYemeClear() {
    const elements = document.getElementsByClassName('yemi-yeme');
    while (elements.length > 0) elements[0].remove();
  }


  function setTweetListLength(newTweetListLength) {
    oldTweetListLength = newTweetListLength;
    pageLoad();
  }


  function filterNewTweetList(tweetList) {
    let filterTweetList = [];
    let k = 0;

    for (let i = 0; i < tweetList.length; i++) {
      let yemiYeme = tweetList[i].parentElement.getElementsByClassName('yemi-yeme');
      if (yemiYeme.length === 0) {
        filterTweetList[k] = tweetList[i];
        k++;
      }
    }
    return filterTweetList;
  }

  function pageLoad() {


    let tweetList = document.getElementsByClassName('TweetTextSize js-tweet-text tweet-text');


    tweetList = filterNewTweetList(tweetList);


    let filterTweetList = doFilterTweetList(tweetList);


    for (let tweet in filterTweetList) {

      let selectedText = selectedTextPreProcess(filterTweetList[tweet].innerText);

      if (selectedText && selectedText.length > 0) {

        let xhttp = new XMLHttpRequest();
        let url = "https://yemiyeme.xyz:8443/api/yemi-yeme?source=" + selectedText;

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
    let i = 0;
    for (let tweet in tweetList) {
      if (isTweetLangEqualsTR(tweetList[tweet]) && isTweetChildElementNews(tweetList[tweet])) {
        filterTweetList[i] = tweetList[tweet];
        i++;
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
