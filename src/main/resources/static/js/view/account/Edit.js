define([
        'underscore',
        'bootstrap',
        'bootstrap-modal',
        'router',
        'view/BaseForm',
        'tpl!template/account/edit.html',
        'collection/Accounts',
        'model/Account',
        'view/permission/Edit'],
    function (
        _,
        Bootstrap,
        BootstrapModal,
        router,
        BaseFormView,
        EditTemplate,
        Accounts,
        Account,
        EditPermission) {

  return BaseFormView.extend({
    template: EditTemplate,
    events: {
      'click #delete-account' : 'deleteAccount',
      'click #permissions' : 'changePermissions'
    },

    changePermissions: function (e) {
      new Backbone.BootstrapModal(new EditPermission(this.model).getModalOptions()).open();
    },

    deleteAccount: function (e) {
      var confirmed = confirm("Você tem certeza que quer apagar esta conta?");

      if (confirmed === true) {
        this.model.destroy({
          wait:true,
          success: function () {
            router.navigate('/',{trigger:true});
          },
          error: function () {
            console.error(arguments);
            alert('Desculpe, aconteceu um erro enquanto tentávamos apagar a conta.');
          }
        });
      }
    },

    initialize: function (accountId) {
      var _this = this;
      this.loading = true;

      this.collection = new Accounts();
      this.model = new Account();

      this.collection.fetch().then(function () {
        _this.loading = false;

        if (accountId != 'new') {
          _this.model = _this.collection.get(accountId);
          _this.data.title = 'Editar Conta';
        } else {
          _this.data.title = 'Nova Conta';
        }

        _this.render();
      });
    },
  });
});