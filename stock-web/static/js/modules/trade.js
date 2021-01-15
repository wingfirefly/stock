function renderTradeMenu(current) {
  var arr = [
    { id: 1, title: '登录', url: '/trade/login.html' },
    { id: 2, title: '交易规则', url: '/trade/ruleList.html' },
    { id: 3, title: '交易配置', url: '/trade/configList.html' },
    { id: 4, title: '我的持仓', url: '/trade/stockList.html' },
    { id: 5, title: '我的委托', url: '/trade/orderList.html' },
    { id: 6, title: '我的成交', url: '/trade/dealList.html' },
    { id: 7, title: '历史成交', url: '/trade/hisDealList.html' },
    { id: 8, title: '银证转账', url: '/trade/transfer.html', state: 0 }
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
