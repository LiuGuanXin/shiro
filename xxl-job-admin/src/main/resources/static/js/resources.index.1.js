$(function() {

    $(document).ready( function () {
        $('#resources_list').DataTable();
    } );
    // table data
    var tableData = {};
    // init date tables
    var resourcesTable = $("#resources_list").dataTable({
        "deferRender": true,
        "processing" : true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/resources/pageList" ,
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
                "width":'8%'
                // ,
                // "render": function ( data, type, row ) {
                //
                //     return data;
                // }
            },
            {
                "data": 'name',
                "width":'12%'
                // ,
                // "render": function ( data, type, row ) {
                //     return data;
                // }
            },
            {
                "data": 'parentid',
                "width":'12%'
                // ,
                // "render": function ( data, type, row ) {
                //     return data;
                // }
            },
            {
                "data": 'resurl',
                "width":'12%'
                // ,
                // "render": function ( data, type, row ) {
                //     return data;
                // }
            },
            {
                "data": 'type',
                "width":'12%'
                // ,
                // "render": function ( data, type, row ) {
                //     return data;
                // }
            },

            // {
            //     "data": 'sort',
            //     "width":'12%'
            //     // ,
            //     // "render": function ( data, type, row ) {
            //     //     return data;
            //     // }
            // },
            {
                "data": I18n.system_opt ,
                "width":'7%',
                "render": function ( data, type, row ) {
                    return function(){
                        tableData['key'+row.id] = row;
                        var html = '<p id="'+ row.id +'" >'+
                            '<button class="btn btn-warning btn-xs update" type="button">'+ I18n.system_opt_edit +'</button>  '+
                            '<button class="btn btn-danger btn-xs resources_operate" _type="resources_del" type="button">'+ I18n.system_opt_del +'</button>  '
                            + '</p>';

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
        resourcesTable.fnDraw();
    });
    // add
    $(".add").click(function(){
        $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    var addModalValidate = $("#addModal .form").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : true,
        rules : {
            id: {

            },
            name : {
                required : true
            },
            parentid : {
                required : true,
                maxlength: 5
            },
            resurl : {
                required : true
            },
            type : {
                required : true
            }
        },
        messages : {
            id:{

            },
            name : {
                required : "请输入资源名称"
            },
            parentid : {
                required : "请输入父ID"
            },
            resurl : {
                required : "请输入资源链接"
            },
            type : {
                required : "请输入资源类型"
            }
        },
        highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success : function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        },
        submitHandler : function(form) {

            $.post(
                base_url + "/resources/add",
                $("#addModal .form").serialize(), function(data, status) {
                    if (data.code == "200") {
                        $('#addModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: I18n.system_add_suc ,
                            icon: '1',
                            end: function(layero, index){
                                resourcesTable.fnDraw();
                                //window.location.reload();
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: (data.msg || I18n.system_add_fail),
                            icon: '2'
                        });
                    }
                });
        }
    });
    $("#addModal").on('hide.bs.modal', function () {
        $("#addModal .form")[0].reset();
        addModalValidate.resetForm();
        $("#addModal .form .form-group").removeClass("has-error");
        $(".remote_panel").show();	// remote

        // $("#addModal .form input[name='executorHandler']").removeAttr("readonly");
    });
    // update
    $("#resources_list").on('click', '.update',function() {

        var id = $(this).parent('p').attr("id");
        var row = tableData['key'+id];

        // base data
        $("#addModal .form input[name='id']").val(id);
        $("#addModal .form input[name='name']").val( row.name );
        $("#addModal .form input[name='parentid']").val( row.parentid );
        $("#addModal .form input[name='resurl']").val( row.resurl );
        $('#addModal .form select[name=type] option[value='+ row.type +']').prop('selected', true);

        // show
        $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    // job operate
    $("#resources_list").on('click', '.resources_operate',function() {
        var typeName;
        var url;
        var needFresh = false;

        var type = $(this).attr("_type");
        if ("resources_del" === type) {
            typeName = I18n.system_opt_del ;
            url = base_url + "/resources/delete";
            needFresh = true;
        } else {
            return;
        }

        var id = $(this).parent('p').attr("id");

        layer.confirm( I18n.system_ok + typeName + '?', {
            icon: 3,
            title: I18n.system_tips ,
            btn: [ I18n.system_ok, I18n.system_cancel ]
        }, function(index){
            layer.close(index);

            $.ajax({
                type : 'POST',
                url : url,
                data : {
                    "id" : id
                },
                dataType : "json",
                success : function(data){
                    if (data.code == 200) {

                        layer.open({
                            title: I18n.system_tips,
                            btn: [ I18n.system_ok ],
                            content: typeName + I18n.system_success ,
                            icon: '1',
                            end: function(layero, index){
                                if (needFresh) {
                                    //window.location.reload();
                                    resourcesTable.fnDraw(false);
                                }
                            }
                        });
                    } else {
                        layer.open({
                            title: I18n.system_tips,
                            btn: [ I18n.system_ok ],
                            content: (data.msg || typeName + I18n.system_fail ),
                            icon: '2'
                        });
                    }
                }
            });
        });
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
