package com.masai.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.TenderException;
import com.masai.model.Tender;
import com.masai.repository.TenderRepository;
import com.masai.repository.VendorRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private TenderRepository tenderRepository;

	@Autowired
	private VendorRepository vendorRepository;

	// ---------- A D D - N E W - T E N D E R ---------- //

	@Override
	public Tender createTender(Tender tender) throws TenderException {

		Optional<Tender> opt = null;
		if (tender.getTenderId() != null)
			opt = tenderRepository.findById(tender.getTenderId());

		if (opt != null && opt.isPresent()) {
			throw new TenderException("Tender Already Created");
		} else {
			Tender savedTender = tenderRepository.save(tender);
			return savedTender;
		}
	}

	// ---------- R E M O V E - E X I S T I N G - T E N D E R ---------- //

	@Override
	public Tender removeTender(Integer tenderId) throws TenderException {
		Optional<Tender> opt = tenderRepository.findById(tenderId);
		if (!opt.isPresent())
			throw new TenderException("Tender with this Tender ID doesn't exist");

		tenderRepository.delete(opt.get());
		return opt.get();
	}

	// ---------- U P D A T E - E X I S T I N G - T E N D E R ---------- //

	@Override
	public Tender updateTender(Tender tender, Integer id) throws TenderException {
		Optional<Tender> t = tenderRepository.findById(id);

		Tender t1 = t.orElseThrow(() -> new TenderException("Not valid Id"));
		t1.setTitle(tender.getTitle());
		t1.setDescription(tender.getDescription());
		t1.setTenderPrice(tender.getTenderPrice());
		t1.setDurationInDays(tender.getDurationInDays());

		return tenderRepository.save(t1);
	}

	// ---------- G E T - A L L - T E N D E R S ---------- //

	@Override
	public List<Tender> viewAllTenders() throws TenderException {
		List<Tender> tenders = tenderRepository.findAll();
		if (tenders.size() == 0) {
			throw new TenderException("No Tenders available");
		} else {
			return tenders;
		}
	}

	// ---------- G E T - T E N D E R - B Y - I D ---------- //

	@Override
	public Tender viewTendersById(Integer tenderId) throws TenderException {
		Optional<Tender> opt = tenderRepository.findById(tenderId);
		if (opt.isPresent()) {
			Tender viewTender = opt.get();
			return viewTender;
		} else {
			throw new TenderException("Tender not Found");
		}
	}

	// ---------- G E T - T E N D E R S - B Y - S T A T U S ---------- //

	@Override
	public List<Tender> viewTendersByStatus(String status) throws TenderException {
		List<Tender> tenders = tenderRepository.findAll();

		List<Tender> tenderListByStatus = tenders.stream().filter(t -> t.getStatus().equals(status))
				.collect(Collectors.toList());

		if (tenderListByStatus.size() == 0) {
			throw new TenderException("No Tenders available with the status " + status);
		} else {
			return tenderListByStatus;
		}
	}

}
