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
                            '<button class="btn btn-info btn-xs user_role" type="button">'+ '分配权限' +'</button>  '+
                            // '<button class="btn btn-warning btn-xs update" type="button">'+ I18n.system_opt_edit +'</button>  '+
                            '<button class="btn btn-danger btn-xs role_operate" _type="role_del" type="button">'+ I18n.system_opt_del +'</button>  '
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

    $("#role_list").on('click', '.user_role',function() {
        //弹出选择角色的框
        var id = $(this).parent('p').attr("id");
        roleId = id;
        var setting = {
            check: {
                enable: true,
                chkboxType:  { "Y" : "p", "N" : "s" }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentid",
                }
            }
        };
        $.ajax({
            async:false,
            type : "POST",
            data:{rid:id},
            url: "resources/resourcesWithSelected",
            dataType:'json',
            success: function(data){

                $.fn.zTree.init($("#treeDemo"), setting, data);
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.expandAll(true);
                $('#selectResources').modal();
            }
        });
        $('#addRole').modal();
    });

    // search Btn
    $('#searchBtn').on('click', function(){
        roleTable.fnDraw();
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
            roleDesc : {
                required : true,
                maxlength: 10
            }
        },

        messages : {
            roleDesc : {
                required : "请输入角色名"
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
                base_url + "/roles/add",
                $("#addModal .form").serialize(), function(data, status) {
                    if (data.code == "200") {
                        $('#addModal').modal('hide');
                        layer.open({
                            title: I18n.system_tips ,
                            btn: [ I18n.system_ok ],
                            content: I18n.system_add_suc ,
                            icon: '1',
                            end: function(layero, index){
                                roleTable.fnDraw();
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

        $("#addModal .form input[name='executorHandler']").removeAttr("readonly");
    });

    // job operate
    $("#role_list").on('click', '.role_operate',function() {
        var typeName;
        var url;
        var needFresh = false;

        var type = $(this).attr("_type");
        if ("role_del" === type) {
            typeName = I18n.system_opt_del ;
            url = base_url + "/roles/delete";
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
                                    roleTable.fnDraw(false);
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
