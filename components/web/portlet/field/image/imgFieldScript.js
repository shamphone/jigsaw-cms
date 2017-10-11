function scalingImg(imgObj,imgWidth,imgHeight){
	if(imgObj!=null&&imgWidth>0&&imgHeight>0){
		var theWidth = imgObj.width;
		var theHeight = imgObj.height;
		if(theWidth/theHeight>theHeight/theWidth){
			if(theWidth>imgWidth){
				imgObj.width = imgWidth;
				imgObj.height = theHeight*imgWidth/theWidth;
			}else{
				imgObj.width = theWidth;
				imgObj.height = theHeight;
			}
		}else{
			if(theHeight>imgHeight){
				imgObj.width = theWidth*imgHeight/theHeight;
				imgObj.height = imgHeight;
			}else{
				imgObj.width = theWidth;
				imgObj.height = theHeight;
			}
		}
	}
}