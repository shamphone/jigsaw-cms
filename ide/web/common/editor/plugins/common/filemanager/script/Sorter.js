
    function _GetSorter(sPropName, sPropType)
    {
      return function compareRes(res1, res2)
      {
        var ret;
        var r1 = _Convert(res1[sPropName], sPropType);
        var r2 = _Convert(res2[sPropName], sPropType);
        if (r1 < r2)
          ret = -1;
        else if (r1 > r2)
          ret = 1;
        else
          ret = 0;
        if (_sSortType == "desc")
          ret = -ret;
        return ret;
      }
    }

    function _Convert(sValue, sType)
    {
      switch (sType)
      {
        case "int":
          return parseInt(sValue);
        case "float":
          return parseFloat(sValue);
        case "date":
          return new Date(Date.parse(sValue));
        default :
          return sValue.toString();
      }
    }

    function SetSortBasis(value_)
    {
      _sSortBasis = value_;
    }

    function SetSortType(value_)
    {
      _sSortType = value_;
    }

    function SetRenderPattern(value_)
    {
      _sRenderPattern = value_;
    }

    function ReverseResources()
    {
      _resources.reverse();
    }
