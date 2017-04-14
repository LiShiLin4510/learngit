package com.sunvsoft.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sunvsoft.entity.MemberActivation;

public class bnMemberListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MemberActivation.getInstance().init();
	}


}
