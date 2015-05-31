define(['jquery'], function ($) {
  var newAuth = {
    email: window.email,
    expires: window.expires,
    token: window.token
  };

  delete window.email;
  delete window.expires;
  delete window.token;

  if (newAuth.token) { // New auth is available
    localStorage.email = newAuth.email;
    localStorage.expires = newAuth.expires;
    localStorage.token = newAuth.token;
  }

  var storageAuth = {
    email: localStorage.email,
    expires: localStorage.expires,
    token: localStorage.token
  };

  // If stored auth is expired
  if (storageAuth.expires < new Date().getTime()) {
    storageAuth = {email: null, expires:null, token:null};
  }

  var Security = {
    isLoggedIn : function () {
      return storageAuth.token != null;
    }
  };

  if (Security.isLoggedIn()) {
    $.ajaxSetup({
      beforeSend: function (xhr) {
        xhr.setRequestHeader("X-Auth-Token", storageAuth.token);
      }
    });
  }

  return Security;
});