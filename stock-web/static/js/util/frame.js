var pageConfiguration = {
  needAuth: false
};

$(function() {
  if (pageConfiguration.needAuth) {
    if (!isLocalAuth()) {
      LocationUtil.goto('/login.html');
      return;
    }
  }

  renderHead();
  renderFoot();

  if (onload) {
    onload();
  }

  function renderHead() {
    var content = '<div class="innerBox"><ul class="top-head">';
    if (!isLocalAuth()) {
      content += '<li><a href="/user/login.html">登录</a></li>';
    } else {
      content += '<li><a href="/user/profile.html">个人中心</a></li>';
      content += '<li><a href="/user/logout.html">注销</a></li>';
    }
    content += '</ul></div>';
    $('#head').html(content);
  }

  function renderFoot() {
    var content = '<div class="innerBox">';
    content += '</div>';
    $('#foot').html(content);
  }

  function isLocalAuth() {
    /*var token = StorageUtil.get(GlobalConsts.authTokenKey);
    return token && token != null && token.length > 10;*/
    return true;
  }

});
