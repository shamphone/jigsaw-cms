
		Array.prototype.IndexOf = function(oItem_)
		{
				for (var i=0; i<this.length; i++)
				{
					if (this[i] == oItem_)
						return i;
				}
				return -1;
		}

		function ViewerItem(sName_, sLength_, sMime_, sMimePrefix_, sMimeSuffix_, sCreatedTime_, sLengthWithUnits_, sPath)
		{
			this._sName = sName_;
			this._sLength = sLength_;
                        this._sLengthWithUnits = sLengthWithUnits_;
                        this._sMime = sMime_;
                        this._sMimePrefix = sMimePrefix_;
                        this._sMimeSuffix = sMimeSuffix_;
                        this._sCreatedTime = sCreatedTime_;
			this._bSelected = false;
                        this.oOwner = null;
                        this._sPath = sPath;
			this._Initialize();
		}

		ViewerItem.prototype =
		{
			CLICK_EVENT : "ClickEvent",
			_ClickEventItems : [],

			OnClick : function() {},

			_OnClick : function()
			{
				if (this.OnClick())
					return;

				if (this._bSelected)
				  return;

				for (var i=0; i<this._ClickEventItems.length; i++)
				{
					var oItem = this._ClickEventItems[i];
					if (oItem.GetSelected())
					{
						var oOwner = oItem.GetOwner();
						oOwner.style.backgroundColor = "";
						oOwner.style.color = "";
						oItem.SetSelected(false);
					}
				}
				this._oOwner.style.backgroundColor = "#0000ff";
				this._oOwner.style.color = "#ffffff";
				this._bSelected = true;
			},

			AttachEvent : function(event_)
			{
				if (this._ClickEventItems.IndexOf(this) > -1)
					return;

				switch (event_)
				{
					case this.CLICK_EVENT :
						this._ClickEventItems.push(this);
						break;
				}
			},

			_Initialize : function()
			{
				this.AttachEvent(this.CLICK_EVENT);
			},

			GetName : function()
			{
				return this._sName;
			},
			GetLength : function()
			{
				return this._sLength;
			},
			GetLengthWithUnits : function()
			{
				return this._sLengthWithUnits;
			},
			GetMime : function()
			{
				return this._sMime;
			},
			GetMimePrefix : function()
			{
				return this._sMimePrefix;
			},
			GetMimeSuffix : function()
			{
				return this._sMimeSuffix;
			},
			GetCreatedTime : function()
			{
				return this._sCreatedTime;
			},
			GetSelected : function()
			{
				return this._bSelected;
			},
			GetOwner : function()
			{
				return this._oOwner;
			},
			GetPath : function()
			{
				return this._sPath;
			},

			SetName : function(value_)
			{
				this._sName = value_;
			},
			SetLength : function(value_)
			{
				this._sLength = value_;
			},
			SetLengthWithUnits : function(value_)
			{
				this._sLengthWithUnits = value_;
			},
			SetMime : function(value_)
			{
				this._sMime = value_;
			},
			SetMimePrefix : function(value_)
			{
				this._sMimePrefix = value_;
			},
			SetMimeSuffix : function(value_)
			{
				this._sMimeSuffix = value_;
			},
			SetCreatedTime : function(value_)
			{
				this._sCreatedTime = value_;
			},
			SetSelected : function(value_)
			{
				this._bSelected = value_;
			},
			SetOwner : function(value_)
			{
				this._oOwner = value_;
			},
			SetPath : function(value_)
			{
				this._sPath = value_;
			}
		}
