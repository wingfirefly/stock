function renderAdminMenu(current) {
  var arr = [
    { id: 1, title: '用户管理', url: '/admin/business/userList.html' }
  ];

  renderMenu(arr, '.menu-nav', current);
}
