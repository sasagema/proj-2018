package com.example.project2018.pki.service;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.operator.OperatorCreationException;

import com.example.project2018.pki.model.SSCertificate;


public interface SSCertificateService {

	
	public SSCertificate createSSCertificate(SSCertificate cert);

	public SSCertificate createIMCertificate(SSCertificate cert);
	
	boolean isValid(String serialNumber);
	
	Certificate revoke(String serialNumber,String issuerAlias, String issuerPassword) throws CRLException, IOException, OperatorCreationException;
	 
}

