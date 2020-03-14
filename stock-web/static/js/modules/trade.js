function renderTradeMenu(current) {
  var arr = [
    { id: 1, title: '登录', url: '/trade/login.html' },
    { id: 2, title: '交易规则', url: '/trade/ruleList.html' },
    { id: 3, title: '交易配置', url: '/trade/configList.html' },
    { id: 4, title: '交易处理', url: '/trade/dealList.html' }
  ];

  renderMenu(arr, '.menu-nav', current);
}
