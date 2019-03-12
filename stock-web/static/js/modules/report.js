function renderReportMenu(current) {
  var arr = [
    { id: 1, title: '股票列表', url: '/report/stockList.html' },
    { id: 2, title: '数据统计', url: '/report/dailyList.html' },
    { id: 3, title: '股票记录', url: '/report/stockLogList.html' }
  ];

  renderMenu(arr, '.menu-nav', current);
}
