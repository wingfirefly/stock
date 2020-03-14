function renderTradeMenu(current) {
  var arr = [
    { id: 1, title: '定时任务', url: '/system/taskList.html' }
  ];

  renderMenu(arr, '.menu-nav', current);
}
