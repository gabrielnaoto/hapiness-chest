$('#btn-incluir').puibutton({
     icon: 'fa-external-link-square'
    ,click: function () {
        $('#dlg').puidialog('show');
    }
});

$('#dlg').puidialog({
     showEffect : 'fade'
    ,hideEffect : 'fade'
    ,minimizable: false
    ,maximizable: true
    ,responsive : true
    ,width      : 400
    ,modal      : true
});

function handleFormRequest(args) {
    console.log(arguments);
    if(!args || !args.validationFailed) {
        PF('dlg').hide();
    }
}