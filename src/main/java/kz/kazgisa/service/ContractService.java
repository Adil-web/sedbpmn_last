package kz.kazgisa.service;

import kz.kazgisa.data.entity.contract.Contract;
import kz.kazgisa.data.entity.contract.ContractStatusHistory;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.ContractStatus;
import kz.kazgisa.data.repositories.contract.ContractPartyRepository;
import kz.kazgisa.data.repositories.contract.ContractRepository;
import kz.kazgisa.data.repositories.contract.ContractStatusHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractStatusHistoryRepository contractStatusHistoryRepository;
    private final UserService userService;
    private final ContractPartyRepository contractPartyRepository;

    public ContractService(ContractRepository contractRepository, ContractStatusHistoryRepository contractStatusHistoryRepository, UserService userService, ContractPartyRepository contractPartyRepository) {
        this.contractRepository = contractRepository;
        this.contractStatusHistoryRepository = contractStatusHistoryRepository;
        this.userService = userService;
        this.contractPartyRepository = contractPartyRepository;
    }

    public Contract saveContract(Contract contract) {
        if(contract.getStatusHistory()!=null && !contract.getStatusHistory().getStatus().equals(ContractStatus.DRAFT))
            throw new ForbiddenException();
        if(contract.getCustomer() != null) {
            contract.setCustomer(contractPartyRepository.save(contract.getCustomer()));
        }
        if(contract.getOrganizer() != null) {
            contract.setOrganizer(contractPartyRepository.save(contract.getOrganizer()));
        }
        if(contract.getAuthor()== null) {
            User user = userService.getCurrentUser();
            contract.setAuthor(user);
        }
        if(contract.getCreateDate()==null){
            contract.setCreateDate(new Date());
        }
        contract.setDeleted(false);
        contract = contractRepository.save(contract);
        if(contract.getStatusHistory() == null) {
            ContractStatusHistory statusHistory = new ContractStatusHistory();
            statusHistory.setDateTime(ZonedDateTime.now());
            statusHistory.setStatus(ContractStatus.DRAFT);
            statusHistory.setContract(contract);
            contract.setStatusHistory(contractStatusHistoryRepository.save(statusHistory));
            contractRepository.save(contract);
        }
        return contract;
    }

    public Page<Contract> getContracts(Pageable pageable) {
        return contractRepository.findByDeletedFalse(pageable);
    }

    public Contract getContractById(Long id) {
        return contractRepository.findById(id).orElseGet(() -> {
            throw new NotFoundException();
        });
    }

    public Contract deleteContractById(Long id) {
        contractRepository.findById(id).ifPresent(contract -> {
            contract.setDeleted(true);
            contractRepository.save(contract);
        });
        return new Contract();
    }
}
