$('.btn-editar').puibutton({
    icon: 'fa-external-link-square'
    ,click: function () {
        setTimeout(
             $('#dlg').puidialog('show')
            ,1000000000
        );
    }
});