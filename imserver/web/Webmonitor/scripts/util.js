		function setTime(f){
			f.target.value='query';
			f.startTime.value = f.startMonth.options[f.startMonth.selectedIndex].value
				 + f.startDay.options[f.startDay.selectedIndex].value
				 + f.startYear.options[f.startYear.selectedIndex].value
				 + f.startHour.options[f.startHour.selectedIndex].value
				 + f.startMinute.options[f.startMinute.selectedIndex].value
				 + f.startMeridiem.options[f.startMeridiem.selectedIndex].value;
			f.endTime.value = f.endMonth.options[f.endMonth.selectedIndex].value
				 + f.endDay.options[f.endDay.selectedIndex].value
				 + f.endYear.options[f.endYear.selectedIndex].value
				 + f.endHour.options[f.endHour.selectedIndex].value
				 + f.endMinute.options[f.endMinute.selectedIndex].value
				 + f.endMeridiem.options[f.endMeridiem.selectedIndex].value;
			return true;				 
		}

		function setTimeDay(f){
			f.target.value='query';
			f.startTime.value = f.startMonth.options[f.startMonth.selectedIndex].value
				 + f.startDay.options[f.startDay.selectedIndex].value
				 + f.startYear.options[f.startYear.selectedIndex].value
			f.endTime.value = f.endMonth.options[f.endMonth.selectedIndex].value
				 + f.endDay.options[f.endDay.selectedIndex].value
				 + f.endYear.options[f.endYear.selectedIndex].value
			return true;				 
		}
