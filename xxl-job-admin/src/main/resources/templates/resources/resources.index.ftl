<!DOCTYPE html>
<html>
<head>
  	<#import "../common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
	<!-- DataTables -->
  	<link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
  	<!-- daterangepicker -->
  	<link rel="stylesheet" href="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.css">
    <title>${I18n.admin_name}</title>
    <#--<style type="text/css">-->
        <#--#userRole{-->
            <#--width:100px;float:right;-->
            <#--margin-right: 12px;-->
        <#--}-->
        <#--#userDel {-->
            <#--float:right:-->
            <#--margin-right: 12px;-->
        <#--}-->
        <#--#userAdd {-->
            <#--float:right;-->
            <#--margin-right: 12px;-->
        <#--}-->
        <#--#butgroup {-->
            <#--width:260px;-->
            <#--float:right;-->
            <#--overflow: hidden;-->
        <#--}-->
    <#--</style>-->
</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && cookieMap["xxljob_adminlte_settings"]?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if> ">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "resources" />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>资源管理</h1>
		</section>
		
		<!-- Main content -->
	    <section class="content">
	    	<div class="row">
                <div class="col-xs-3" style="width: 23%;margin-left:12px;padding: 0 ">
                    <div class="input-group">
                        <span class="input-group-addon">${I18n.user_field_num}</span>
                        <input type="text" class="form-control" id="id" autocomplete="on" >
                    </div>
                </div>
                <div class="col-xs-3" class="col-xs-3" style="width: 22%;padding: 0">
                    <div class="input-group">
                        <span class="input-group-addon">${I18n.jobinfo_field_name}</span>
                        <input type="text" class="form-control" id="username" autocomplete="on" >
                    </div>
                </div>
                <div class="col-xs-2" style="padding: 0">
                    <div class="input-group">
                        <span class="input-group-addon">${I18n.user_status}</span>
                        <select class="form-control" id="enable" >
                            <option value="2" >${I18n.user_status_all}</option>
                            <option value="1" >${I18n.user_status_open}</option>
                            <option value="0" >${I18n.user_status_close}</option>
                        </select>
                    </div>
                </div>
                <div class="col-xs-1" style="padding: 0;width: 6%;margin-left: 5px">
                    <button class="btn btn-block btn-info" id="searchBtn">${I18n.system_search}</button>
                </div>
                <div style="width:260px;float: right;overflow: hidden">
                    <#--<button id="userRole" class="btn btn-default" style="float: right;margin-right:15px;width:100px">${I18n.user_role}</button>-->
                    <#--<button id="userDel"  class="btn btn-default" style="float: right;margin-right:2px">${I18n.user_del}</button>-->
                    <button id="userAdd"  class="btn btn-default add" style="float: right;margin-right:15px;width:100px">${I18n.user_add}</button>
                </div>
          	</div>
			
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <#--<div class="box-header hide"><h3 class="box-title">调度日志</h3></div>-->
			            <div class="box-body">
			              	<table id="resources_list" class="table table-bordered table-striped display" width="100%" >
				                <thead>
					            	<tr>
                                        <th name="id" >角色编号</th>
                                        <th name="name" >资源名称</th>
                                        <th name="parentid" >父资源</th>
                                        <th name="resurl" >资源链接</th>
                                        <th name="type" >资源类型</th>
                                        <th name="operation" >操作</th>
					                </tr>
				                </thead>
				                <tbody>

                                <#--<#if list?exists && list?size gt 0>-->
								<#--<#list list as l>-->
									<#--<tr>-->
                                    <#--&lt;#&ndash;<td>${group.id}</td>&ndash;&gt;-->
                                        <#--<td>${l.id}</td>-->
                                        <#--<td>${l.username}</td>-->
                                        <#--<td>${l.enable}</td>-->
                                    <#--</tr>-->
                                <#--</#list>-->
                                <#--</#if>-->

                                </tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	    </section>
	</div>
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" >添加资源</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form" >
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">资源名称<font color="red">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="userName" placeholder="请输入资源名称" maxlength="10" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">父资源ID<font color="red">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="password" placeholder="请输入父资源ID" maxlength="5" ></div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label">资源链接<font color="red">*</font></label>
                            <div class="col-sm-4"><input type="text" class="form-control" name="resurl" placeholder="请输入资源链接" maxlength="30" ></div>
                        </div>
                        <div class="form-group">
                            <label for="firstname" class="col-sm-2 control-label">资源类型<font color="red">*</font></label>
                            <div class="col-sm-4">
                                <select class="form-control" name="resType" >
                                    <option value="menu" >菜单</option>
                                    <option value="but" >按钮</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary"  >${I18n.system_save}</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">${I18n.system_cancel}</button>
                                <input type="hidden" name="id" >
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
	<!-- footer -->
	<@netCommon.commonFooter />
</div>


<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<!-- daterangepicker -->
<script src="${request.contextPath}/static/adminlte/bower_components/moment/moment.min.js"></script>
<script src="${request.contextPath}/static/adminlte/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
<script src="${request.contextPath}/static/js/resources.index.1.js"></script>
</body>
</html>
