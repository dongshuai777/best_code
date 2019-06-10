var url=window.location.host;
//url+=window.location.protocol;
<!-- Tell the browser to be responsive to screen width -->
    document.write('<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">');
<!-- Bootstrap 3.3.7 -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/bootstrap/dist/css/bootstrap.min.css">');
<!-- Font Awesome -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/font-awesome/css/font-awesome.min.css">');
<!-- Ionicons -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/Ionicons/css/ionicons.min.css">');
<!-- Theme style -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/dist/css/AdminLTE.min.css">');
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/dist/css/skins/_all-skins.min.css">');
<!-- Morris chart -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/morris.js/morris.css">');
<!-- jvectormap -->
    // document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/jvectormap/jquery-jvectormap.css">');
<!-- Date Picker -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">');
<!-- Daterange picker -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/bootstrap-daterangepicker/daterangepicker.css">');
<!-- bootstrap wysihtml5 - text editor -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">');
<!-- dataTable css -->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/datatables.net-bs/css/dataTables.bootstrap.css">');
<!-- select2-->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bower_components/select2/dist/css/select2.min.css">');
<!--home.css-->
    document.write('<link rel="stylesheet" href="http://'+url+'/css/home.css">');
<!--bootstrapvalidator-master.css-->
    document.write('<link rel="stylesheet" href="http://'+url+'/js/bootstrapvalidator-master/css/bootstrapValidator.min.css">');



<!-- jQuery 3 -->
    // document.write('<script src="http://'+url+'/js/bower_components/jquery/dist/jquery.min.js"></script>');
    document.write('<script src="http://'+url+'/js/jquery1.9.1.min.js"></script>');
<!-- jQuery UI 1.11.4 -->
    document.write('<script src="http://'+url+'/js/bower_components/jquery-ui/jquery-ui.min.js"></script>');
<!-- Bootstrap 3.3.7 -->
    document.write('<script src="http://'+url+'/js/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>');
<!-- Morris.js charts -->
    document.write('<script src="http://'+url+'/js/bower_components/raphael/raphael.min.js"></script>');
    document.write('<script src="http://'+url+'/js/bower_components/morris.js/morris.min.js"></script>');
<!-- Sparkline -->
    document.write('<script src="http://'+url+'/js/bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>');
<!-- jvectormap -->
    // document.write('<script src="http://'+url+'/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>');
    // document.write('<script src="http://'+url+'/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>');
<!-- jQuery Knob Chart -->
    document.write('<script src="http://'+url+'/js/bower_components/jquery-knob/dist/jquery.knob.min.js"></script>');
<!-- daterangepicker -->
    document.write('<script src="http://'+url+'/js/bower_components/moment/min/moment.min.js"></script>');
    document.write('<script src="http://'+url+'/js/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>');
<!-- datepicker -->
    document.write('<script src="http://'+url+'/js/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>');
<!-- Bootstrap WYSIHTML5 -->
    document.write('<script src="http://'+url+'/js/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>');
<!-- Slimscroll -->
    document.write('<script src="http://'+url+'/js/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>');
<!-- FastClick -->
    document.write('<script src="http://'+url+'/js/bower_components/fastclick/lib/fastclick.js"></script>');
<!-- AdminLTE App -->
    document.write('<script src="http://'+url+'/js/dist/js/adminlte.min.js"></script>');
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    //document.write('<script src="http://'+url+'/js/dist/js/pages/dashboard.js"></script>');
<!-- dataTable js-->
    document.write('<script src="http://'+url+'/js/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>');
    document.write('<script src="http://'+url+'/js/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>');
<!-- select2-->
    document.write('<script src="http://'+url+'/js/bower_components/select2/dist/js/select2.full.min.js"></script>');
<!-- bootstrapvalidator-master.js-->
    document.write('<script src="http://'+url+'/js/bootstrapvalidator-master/js/bootstrapValidator.min.js"></script>');
    document.write('<script src="http://'+url+'/js/bootstrapvalidator-master/js/language/zh_CN.js"></script>');

<!-- layer-->
    document.write('<script src="http://'+url+'/js/layer/layer.js"></script>');
<!-- cookies -->
    document.write('<script src="http://'+url+'/js/jquery.cookie.js"></script>');
<!-- submit -->
    document.write('<script src="http://'+url+'/common/commit.js"></script>');
<!-- pagehelper -->
    document.write('<script src="http://'+url+'/common/table.js"></script>');

<!-- IE8下支持bind方法-->
    document.write(
        '<script type="text/javascript">' +
        'if (!Function.prototype.bind) { ' +
        'Function.prototype.bind = function (oThis) { ' +
        'if (typeof this !== "function") { ' +
        'throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable"); ' +
        '} ' +
        'var aArgs = Array.prototype.slice.call(arguments, 1), ' +
        'fToBind = this, ' +
        'fNOP = function () {}, ' +
        'fBound = function () { ' +
        'return fToBind.apply(this instanceof fNOP && oThis ' +
        '? this ' +
        ': oThis, ' +
        'aArgs.concat(Array.prototype.slice.call(arguments))); ' +
        '}; ' +
        'fNOP.prototype = this.prototype; ' +
        'fBound.prototype = new fNOP(); ' +
        'return fBound; ' +
        '}; ' +
        '} ' +
        '</script>');

