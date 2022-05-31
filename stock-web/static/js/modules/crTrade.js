function renderTradeMenu(current) {
  var arr = [
    { id: 3, title: '我的持仓', url: '/crTrade/stockList.html' },
    { id: 4, title: '我的委托', url: '/crTrade/orderList.html' },
    { id: 5, title: '我的成交', url: '/crTrade/dealList.html' },
    { id: 6, title: '历史成交', url: '/crTrade/hisDealList.html' }
  ];

  renderMenu(arr, '.menu-nav', current);
}
