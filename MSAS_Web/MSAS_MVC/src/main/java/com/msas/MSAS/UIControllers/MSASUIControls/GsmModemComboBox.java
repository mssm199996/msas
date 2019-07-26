package com.msas.MSAS.UIControllers.MSASUIControls;

import java.util.ArrayList;
import java.util.List;

import jssc.SerialPortList;

import org.springframework.data.domain.Pageable;

import com.msas.MSAS.UIControllers.UsefulModules.MSComboBox.MSSemiBackendComboBox;

public class GsmModemComboBox extends MSSemiBackendComboBox<String> {

	private static final long serialVersionUID = 5185283332531328317L;

	@Override
	public List<String> fetchFromDatabase(Pageable pageable) {
		String[] elements = SerialPortList.getPortNames();

		ArrayList<String> elementsAsList = new ArrayList<>(elements.length);

		for (String element : elements)
			elementsAsList.add(element);

		return elementsAsList;
	}

	@Override
	public int fetchCount() {
		return SerialPortList.getPortNames().length;
	}
}
