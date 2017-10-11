#include <windows.h>
#include <stdlib.h>
#include <stdio.h>
#include ".\exceptiontarget.h"
#include "MatrixCLib/MatrixC.h"

ExceptionTarget::ExceptionTarget(MatrixC* pMatrixC) : MessageTarget(pMatrixC) 
{
    pMatrixC->registerExceptionHandler( this, static_cast<PMSG_HANDLER>(ExceptionTarget::ExceptionHandler));
}

ExceptionTarget::~ExceptionTarget(void)
{
}

void ExceptionTarget::ExceptionHandler(BaseMessage* pMessage)
{
    printf("Exception happened.\n");
    ::exit(0);
}
