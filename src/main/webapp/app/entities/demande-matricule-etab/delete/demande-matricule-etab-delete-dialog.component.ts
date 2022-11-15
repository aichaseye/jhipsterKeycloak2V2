import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeMatriculeEtab } from '../demande-matricule-etab.model';
import { DemandeMatriculeEtabService } from '../service/demande-matricule-etab.service';

@Component({
  templateUrl: './demande-matricule-etab-delete-dialog.component.html',
})
export class DemandeMatriculeEtabDeleteDialogComponent {
  demandeMatriculeEtab?: IDemandeMatriculeEtab;

  constructor(protected demandeMatriculeEtabService: DemandeMatriculeEtabService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeMatriculeEtabService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
