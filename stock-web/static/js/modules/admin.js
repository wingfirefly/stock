function renderMenu(selector, current) {
  var arr = [
    { id: 1, title: '用户管理', url: '/admin/business/userList.html' }
  ];

  var content = '';
  $.each(arr, function(index, item) {
    if (current === item.id) {
      content += '<li class="current"><a href="javascript:void(0);">' + item.title +'</a></li>';
    } else {
      content += '<li ><a href="' + item.url + '">' + item.title +'</a></li>';
    }
  });

  $(selector).html(content);

}
