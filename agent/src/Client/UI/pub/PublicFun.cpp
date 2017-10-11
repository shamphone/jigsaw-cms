#include "stdafx.h"
#include "PublicFun.h"

void FillGradient(CDC *pDC, CRect rect, COLORREF colorStart, COLORREF colorFinish, BOOL bHorz)
{
	int nShift = 9;
    int nSteps = 1 << nShift;

    for (int i = 0; i < nSteps; i++)
    {
        BYTE bR = (BYTE) ((GetRValue(colorStart) * (nSteps - i) +
                   GetRValue(colorFinish) * i) >> nShift);
        BYTE bG = (BYTE) ((GetGValue(colorStart) * (nSteps - i) +
                   GetGValue(colorFinish) * i) >> nShift);
        BYTE bB = (BYTE) ((GetBValue(colorStart) * (nSteps - i) +
                   GetBValue(colorFinish) * i) >> nShift);
		CBrush br (RGB(bR, bG, bB));
        CRect r2 = rect;
        if (bHorz)
        {
            r2.bottom = rect.bottom - 
                ((i * rect.Height()) >> nShift);
            r2.top = rect.bottom - 
                (((i + 1) * rect.Height()) >> nShift);
            if (r2.Height() > 0)
                pDC->FillRect(r2, &br);
        }
        else
        {
            r2.left = rect.left + 
                ((i * rect.Width()) >> nShift);
            r2.right = rect.left + 
                (((i + 1) * rect.Width()) >> nShift);
            if (r2.Width() > 0)
                pDC->FillRect(r2, &br);
        }
    }
}