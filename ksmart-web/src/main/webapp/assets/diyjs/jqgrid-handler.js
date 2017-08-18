/**
	 * 初始化操作按钮
	 * @param selector 选择器
	 * @param rowId 行ID
	 * @param op       操作
	 */
	function initOpBtn(selector, rowId, op) {
      var  btn1 = "<li><a href='javascript:deleteProducerOfProduceMonitor(" + id + ")'><i class='icon-remove'></i><span class='menu-span'>取消订阅</span></a></li>";

	    if (op && op != "") {
	        var btn = "<div class='btn-group'>" +
	            "<button type='button' class='btn btn-default btn-xs'>操作</button>" +
	            "<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown'>" +
	            "<span class='caret'></span><span class='sr-only'></span></button><ul class='dropdown-menu pull-right' role='menu'>"
	            + btn1 +
	            "</ul></div>";

	        jQuery(selector).jqGrid('setRowData', rowId, {act: btn});
	    }
	}
	
	/**
	 * 构造远程数据加载按钮
	 * @param id 按钮ID
	 * @param modal 页面显示模态框
	 * @param url url
	 * @param style 按钮样式
	 * @param lable 按钮名称
	 * @returns {string}
	 */
	function createRemoteModalUrl(id, modal, url, style, lable) {
	    return "<li><a id='" + id + "' href='#" + modal + "' role='button' class='remoteModal' data-toggle='modal' " +
	        "remote-url='" + url + "' remote-target='#" + modal + "'>" + "<i class='" + style + "'></i><span " +
	        "class='menu-span'>" + lable + "</span></a></li>";
	}	
	function createop(){
		  //构造远程访问url
        var editUrl = "www.jd.com";
        var overviewUrl = "www.jd.com";
        var producerViewUrl = "www.jd.com";
        var userAlarmPolicyUrl = "www.jd.com";
        var userAlarmPolicyViewUrl ="www.jd.com";
        var locationManageUrl ="www.jd.com";
        var sendMessageUrl = "www.jd.com";
        var viewMessageUrl ="www.jd.com";
        var cleanRetryUrl = "www.jd.com";
        var reloadRetryCacheUrl = "www.jd.com";

        var dlqUrl = "www.jd.com";
        var plqUrl = "www.jd.com";
        

        //构造编辑下拉按钮ID
        var editId = "edit_" + id;
        var overviewId = "overview_" + id;
        var producerViewId = "producerView_" + id;
        var userAlarmPolicyId = "userAlarmPolicy_" + id;
        var userAlarmPolicyViewId = "userAlarmPolicyView_" + id;
        var locationManageId = "locationManage_" + id;
        var sendMessageId = "sendMessage" + id;
        var viewMessageId = "viewMessage" + id;
        var cleanRetryId = "cleanRetry" + id;
        var reloadRetryCacheId = "reloadRetryCache" + id;

        var dlqId = "dlqId_" + id;
        var plqId = "plqId" + id; 
    //构造操作按钮
    var edit_btn = createRemoteModalUrl(editId, "editConsumerModal", editUrl, 'icon-pencil', '配置消费策略');
    var overview_btn = createRemoteModalUrl(overviewId, "consumerOverviewModal", overviewUrl, 'icon-eye-open', '消费详情');
    var producerView_btn = createRemoteModalUrl(producerViewId, "consumerGetProducerModal", producerViewUrl, 'icon-eye-open', '生产者信息');
    var userAlarmPolicy_btn = createRemoteModalUrl(userAlarmPolicyId, "userAddAlarmPolicyModal", userAlarmPolicyUrl, 'icon-wrench', '配置报警策略');
    var userAlarmPolicyView_btn = createRemoteModalUrl(userAlarmPolicyViewId, "userAlarmPolicyViewModal", userAlarmPolicyViewUrl, 'icon-eye-open', '查看报警策略');
    var sendMessage_btn = createRemoteModalUrl(sendMessageId, "sendMessageModal", sendMessageUrl, 'icon-inbox', '发送消息');
    var viewMessage_btn = createRemoteModalUrl(viewMessageId, "viewMessageModal", viewMessageUrl, 'icon-inbox', '预览消息');
    var cleanRetry_btn = createRemoteModalUrl(cleanRetryId, "cleanRetryModal", cleanRetryUrl, 'icon-inbox', '清理重试缓存');
    var reloadRetryCache_btn = createRemoteModalUrl(reloadRetryCacheId, "reloadRetryCacheModal", reloadRetryCacheUrl, 'icon-inbox', '重新加载重试缓存');
    var locationManage_btn = "";
    var locationSetting_btn = "";
    var archive_btn = "";
    var deleteConsumer = "";
    var retryLeader_btn = "";
    var cachedRetry_btn ="";
    var archiveQ_btn ="";
    if (isAdmin) {
        archiveQ_btn = "<li><a href='/jmq-web"+"/archive.do?topic="+topic+"' target='_blank'><i " +
                "class='icon-eye-open'></i><span class='menu-span'>归档查询</span></a></li>";
        deleteConsumer = "<li><a href='javascript:deleteProducerOfProduceMonitor(" + id + ")'><i class='icon-remove'></i><span class='menu-span'>取消订阅</span></a></li>";

        locationManage_btn = createRemoteModalUrl(locationManageId, "locationManageModal", locationManageUrl, 'icon-cog', '消费位置管理');
        locationSetting_btn = "<li><a href='javascript:void(0)' onclick='resetOffset( \"" +id+ "\", \"" + topicId + "\", \"" + topic + "\", \"" +app+ "\");'" + "><i class='icon-repeat'></i><span class='menu-span'>消费位置设置</span></a></li>";
    }

    archive_btn = "<li><a href='javascript:openConsumerArchive(" + id + ")'><i class='icon-ok-circle'></i><span class='menu-span'>开启归档</span></a></li>";
    if (archive == '<span class="label label-success">已开启</span>') {
        archive_btn = "<li><a href='javascript:closeConsumerArchive(" + id + ")'><i class='icon-ban-circle'></i><span class='menu-span'>关闭归档</span></a></li>";
    }

    var consume_btn = "<li><a href='javascript:openConsume(" + id + ")'><i class='icon-play'></i><span class='menu-span'>开启消费</span></a></li>";
    if (paused == '<span class="label label-success">开启消费</span>') {
        consume_btn = "<li><a href='javascript:pauseConsume(" + id + ")'><i class='icon-pause'></i><span class='menu-span'>暂停消费</span></a></li>";
    }

    var dlq_btn = createRemoteModalUrl(dlqId, "topicToDlqModal", dlqUrl, 'icon-share-alt', '积压转移');
    var plq_btn = createRemoteModalUrl(plqId, "topicToDlqModal", plqUrl, 'icon-share-alt', '预发布');

    //构建操作的下拉菜单
    var op = consume_btn + edit_btn + userAlarmPolicy_btn + archive_btn + "<li " +
            "class='divider'></li>" + overview_btn + archiveQ_btn + locationManage_btn + //构造操作按钮
            locationSetting_btn + producerView_btn + "<li " +
            "class='divider'></li>" +
            sendMessage_btn +viewMessage_btn+ cleanRetry_btn + reloadRetryCache_btn + dlq_btn + plq_btn + "<li class='divider'></li>" + deleteConsumer;
    return op;
	}