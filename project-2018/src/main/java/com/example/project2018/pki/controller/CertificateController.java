package com.example.project2018.pki.controller;

import java.security.cert.Certificate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.pki.model.SSCertificate;
import com.example.project2018.pki.service.SSCertificateService;



@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

	@Autowired
	private SSCertificateService service;
	
/*	Postman POST zahtev
	{
	    "id": null,
	    "serialNumber": null,
	    "commonName": "cn",
	    "surname": "sn",
	    "givenName": "gn",
	    "organization": "o",
	    "organizationUnit": "ou",
	    "country": "rs",
	    "email": "pera@pera",
	    "startDate": "04/01/2018",
	    "endDate": "04/07/2018",
	    "purpose": "EMAIL",
	    "issuer": null,
	    "ca": false,
	    "aia": null,
	    "cdp": null,
	    "password":123
	}
	{
    "id": null,
    "serialNumber": "612788",
    "commonName": "cn",
    "surname": "sn",
    "givenName": "gn",
    "organization": "o",
    "organizationUnit": "ou",
    "country": "rs",
    "email": "pera@pera",
    "startDate": "04/01/2018",
    "endDate": "04/07/2018",
    "purpose": "EMAIL",
    "password": "123",
    "issuerName": null,
    "issuerEndDate": null,
    "issuerPassword": null,
    "issuerSerialNumber": null,
    "ca": true,
    "aia": "http://localhost:8080/certificate/612788",
    "cdp": "Putanja do CRL liste issuera"
}
*/
	@RequestMapping(value="/addSSC", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addSSCert(@RequestBody SSCertificate cert){
		System.out.println("test");
		System.out.println(cert.getStartDate());
		
		SSCertificate generatedCert = service.createSSCertificate(cert);
		//service.save(generatedCert);
		return new ResponseEntity<>(generatedCert, HttpStatus.OK);
	}
	
	@RequestMapping(value="/addIMC", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addIMCert(@RequestBody SSCertificate cert){
		System.out.println("test");               
		
		service.createIMCertificate(cert);
		
	return new ResponseEntity<>(cert, HttpStatus.OK);
	}
	
	
	@GetMapping("/valid/{id}")
	public ResponseEntity<?> isValid(@PathVariable String id){
			service.isValid(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	 
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<String> getCert(@PathVariable String id){
		System.out.println("test getCert ");               
		
		 
		Certificate cert = service.getCertificate(id);
		System.out.println(cert);
		
	return new ResponseEntity<>(cert.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public ResponseEntity<String> getCerts(){
		
		System.out.println("test getCerts ");            
		List<Certificate> certs = service.getCertificates();
		System.out.println(certs);
		
	return new ResponseEntity<>(certs.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteAll", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteCerts(){
		
		System.out.println("test deleteCerts ");            
		service.deleteAllFromKeyStore();
		
		
	return new ResponseEntity<>("Brisanje uspesno.", HttpStatus.OK);
	}
}