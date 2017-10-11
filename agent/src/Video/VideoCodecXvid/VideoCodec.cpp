#include "stdafx.h"
#include "VideoCodec.h"
//#include "../../xvid/1.1.0/xvid.h"
#include "../../xvid/xvidcore-1.1.0/src/xvid.h"

bool VideoCodec::init()
{
    xvid_gbl_init_t xvid_gbl_init;
    memset(&xvid_gbl_init, 0, sizeof(xvid_gbl_init));
    xvid_gbl_init.version = XVID_VERSION;

    int ret = xvid_global(NULL, XVID_GBL_INIT, &xvid_gbl_init, NULL);
    if( ret != 0 )
    {
		_RPTF1(_CRT_WARN, "Init xvid library failed: %x.\n", ret);
        return false;
    }

    _RPTF1(_CRT_WARN, "Init xvid library version %x successfully.\n", XVID_VERSION);
    return true;
}

