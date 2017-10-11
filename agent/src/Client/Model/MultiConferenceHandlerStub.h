#pragma once

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

class MultiConferenceMatrixC;

class MultiConferenceHandlerStub : public LyvcMessage::MessageTarget 
{
public:
	MultiConferenceHandlerStub( MultiConferenceMatrixC* pMultiConferenceMatrixC);
	~MultiConferenceHandlerStub();
	bool create();
	void destroy();

public:

    // Stub handler
    void StubHandler(LyvcMessage::BaseMessage* pMessage);

private:

    // Pointer to MultiConferenceMatrix, Real handler stores here
    MultiConferenceMatrixC* m_pMultiConferenceMatrixC;
};

