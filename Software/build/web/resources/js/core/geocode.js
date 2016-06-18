function geocode() {
    debugger;
    PF('geoMap').geocode(document.getElementById('j_idt20:address').value);
}

$('#gmapDialog').puidialog({
     showEffect : 'fade'
    ,hideEffect : 'fade'
    ,minimizable: false
    ,maximizable: true
    ,responsive : true
    ,width      : 400
    ,modal      : true
});

function teste() {
    debugger;
    var a = 1;
}