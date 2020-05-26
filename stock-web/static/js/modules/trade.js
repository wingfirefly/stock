function renderTradeMenu(current) {
  var arr = [
    { id: 1, title: '登录', url: '/trade/login.html' },
    { id: 2, title: '交易规则', url: '/trade/ruleList.html' },
    { id: 3, title: '交易配置', url: '/trade/configList.html' },
    { id: 4, title: 'cc', url: '/trade/stockList.html' },
    { id: 5, title: 'wt', url: '/trade/orderList.html' },
    { id: 6, title: 'deal', url: '/trade/dealList.html' },
    { id: 7, title: 'his deal', url: '/trade/hisDealList.html' }
  ];

  renderMenu(arr, '.menu-nav', current);
}

function isBusinessTime(date) {
  if (!date) {
    date = new Date();
  }
  var hours = date.getHours();
  if (hours < 9 || hours >= 15 || hours === 12) {
    return false;
  }
  var minutes = date.getMinutes();
  return !(hours === 9 && minutes < 30 || hours === 11 && minutes > 30);
}
