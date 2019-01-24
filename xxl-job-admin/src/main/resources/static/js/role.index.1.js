$(function() {

    $(document).ready( function () {
        $('#role_list').DataTable();
    } );

    // init date tables
    var roleTable = $("#role_list").dataTable({
        "deferRender": true,
        "processing" : true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/roles/pageList" ,
            type:"post",
            data : function ( d ) {
                var obj = {};
                // obj.id = $('#id').val();
                // obj.username = $('#username').val();
                // obj.enable = $('#enable').val();
                obj.start = d.start;
                obj.length = d.length;
                return obj;
            }
        },
        "searching": false,
        "ordering": false,
        "bProcessing":false,
        "columns": [
            {
                "data": 'id',
                "visible" : true,
                "width":'16%'
                // ,
                // "render": function ( data, type, row ) {
                //
                //     return data;
                // }
            },
            {
                "data": 'roleDesc',
                "width":'22%'
                // ,
                // "render": function ( data, type, row ) {
                //     return data;
                // }
            },
            {
                "data": I18n.system_opt ,
                "width":'4%',
                "render": function ( data, type, row ) {
                    return function(){

                        var html = '<p id="'+ row.id +'" >'+
                            '<button class="btn btn-info btn-xs job_per" type="button">'+ '分配权限' +'</button>  '+
                            // '<button class="btn btn-warning btn-xs update" type="button">'+ I18n.system_opt_edit +'</button>  '+
                            '<button class="btn btn-danger btn-xs job_operate" _type="job_del" type="button">'+ I18n.system_opt_del +'</button>  '
                            +

                            '</p>';

                        return html;
                    };
                }
            }
        ],
        "language" : {
            "sProcessing" : I18n.dataTable_sProcessing ,
            "sLengthMenu" : I18n.dataTable_sLengthMenu ,
            "sZeroRecords" : I18n.dataTable_sZeroRecords ,
            "sInfo" : I18n.dataTable_sInfo ,
            "sInfoEmpty" : I18n.dataTable_sInfoEmpty ,
            "sInfoFiltered" : I18n.dataTable_sInfoFiltered ,
            "sInfoPostFix" : "",
            "sSearch" : I18n.dataTable_sSearch ,
            "sUrl" : "",
            "sEmptyTable" : I18n.dataTable_sEmptyTable ,
            "sLoadingRecords" : I18n.dataTable_sLoadingRecords ,
            "sInfoThousands" : ",",
            "oPaginate" : {
                "sFirst" : I18n.dataTable_sFirst ,
                "sPrevious" : I18n.dataTable_sPrevious ,
                "sNext" : I18n.dataTable_sNext ,
                "sLast" : I18n.dataTable_sLast
            },
            "oAria" : {
                "sSortAscending" : I18n.dataTable_sSortAscending ,
                "sSortDescending" : I18n.dataTable_sSortDescending
            }
        }
    });

    // logTips alert
    // $('#joblog_list').on('click', '.logTips', function(){
    //     var msg = $(this).find('span').html();
    //     ComAlertTec.show(msg);
    // });

    // search Btn
    $('#searchBtn').on('click', function(){
        roleTable.fnDraw();
    });
    // add
    $(".add").click(function(){
        $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

});


// Com Alert by Tec theme
var ComAlertTec = {
    html:function(){
        var html =
            '<div class="modal fade" id="ComAlertTec" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content-tec">' +
            '<div class="modal-body"><div class="alert" style="color:#fff;"></div></div>' +
            '<div class="modal-footer">' +
            '<div class="text-center" >' +
            '<button type="button" class="btn btn-info ok" data-dismiss="modal" >'+ I18n.system_ok +'</button>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
        return html;
    },
    show:function(msg, callback){
        // dom init
        if ($('#ComAlertTec').length == 0){
            $('body').append(ComAlertTec.html());
        }

        // init com alert
        $('#ComAlertTec .alert').html(msg);
        $('#ComAlertTec').modal('show');

        $('#ComAlertTec .ok').click(function(){
            $('#ComAlertTec').modal('hide');
            if(typeof callback == 'function') {
                callback();
            }
        });
    }
};
