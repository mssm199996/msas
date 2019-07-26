package com.msas.MSAS.gsm;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import jssc.SerialPortException;
import jssc.SerialPortList;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.msas.MSAS.DomainModel.Configuration.AppConfiguration;
import com.msas.MSAS.DomainModel.Personnel.PersonnelSurveillance;
import com.msas.MSAS.DomainModel.Signal.Intrusion;

@Service
@Configuration
public class GSMModemServices {

	@Autowired
	private AppConfiguration mainConfiguration;

	private GSMModemHandler gsmModemHandler;

	public List<String> getAvailableGsmModems() {
		return Arrays.asList(SerialPortList.getPortNames());
	}

	public void sendIntrusionSms(Intrusion intrusion,
			PersonnelSurveillance personnelSurveillance) {
		if (this.gsmModemHandler != null) {
			String smsContent = "Une intrusion a été détéctée le "
					+ intrusion.getDate().format(
							DateTimeFormatter.ofPattern("dd MMM yyyy"))
					+ " à "
					+ intrusion.getHeure().format(
							DateTimeFormatter.ofPattern("hh:mm:ss"))
					+ " dans la salle " + intrusion.getSalle().getDesignation();

			SMS sms = new SMS();
			sms.setContenuSms(smsContent);
			sms.setCorrespondantSms(personnelSurveillance.getNumeroTelephone());
			sms.setDateSms(LocalDate.now());
			sms.setHeureSms(LocalTime.now());
			sms.setIdSms(1);

			this.gsmModemHandler.sendMessage(sms);
		}
	}

	@PostConstruct
	public void constructGsmModemHandler() {
		String portName = this.mainConfiguration.getGsmPortNumberIntrusions();

		if (portName != null && !portName.equals("")) {
			try {
				this.gsmModemHandler = new GSMModemHandler(portName);
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
}
