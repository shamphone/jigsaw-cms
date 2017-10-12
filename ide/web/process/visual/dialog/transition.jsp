<%@page contentType="text/html; charset=UTF-8"%>
	转移ID：<br />
		<input type="hidden" name="ActionIdhidden" id="ActionIdhidden" value=''"/>
		<input type="text" name="ActionId" id="ActionId" value='' size="25" onchange="checkTransitionId(this.value)"/><br />
		转移名称：<br />
		<input type="text" name="ActionText" id="ActionText" value='' size="25"/><br />
		转移类型:<br />
		<SELECT NAME="transType" class=txtput style="width:185px">
			<option value="NONE">无条件转移</option>
			<option value="CONDITION">条件转移</option>
			<option value="OTHERWISE">其他</option>
			<option value="EXCEPTION">发生异常时转移</option>
			<option value="DEFAULTEXCEPTION">缺省异常转移</option>
		</SELECT>
		起始活动:<br />
		<SELECT NAME="From" class=txtput style="width:185px" onchange="changeStep()"></SELECT>
		目的活动:<br />
		<SELECT NAME="To" class=txtput style="width:185px" onchange="changeStep()"></SELECT>
		内容分类：<br />
		<input type="hidden" name="category" id="category" />                  			
		<input type="text" name="categoryName" readonly="readonly" value="" class="txtput" size="25"/><br />
 		<button class="commonbut" id="searchN" onclick="searchNodeDefinition(document.getElementById('category'),document.getElementById('categoryName'))">选择...</button>
		<input type="hidden" name="condition" id="condition"/><br />
		转移条件：<br />
		<select multiple="multiple" name="filterPatterns" size="5" style="width:185px;"></select><br />
		<button class="commonbut" onclick="newFilter(document.getElementById('category'),document.getElementById('filterPatterns'));setCondition(document.getElementById('filterPatterns'),document.getElementById('condition'));">添 加</button>
  		<button class="commonbut" onclick="deleteOption(document.getElementById('filterPatterns'));setCondition(document.getElementById('filterPatterns'),document.getElementById('condition'));">删 除</button>
		<!-- 转移条件：<br />
		<textarea rows="7" cols="20" id="transitionCondition" name="transitionCondition" style="width:180px;"></textarea>
