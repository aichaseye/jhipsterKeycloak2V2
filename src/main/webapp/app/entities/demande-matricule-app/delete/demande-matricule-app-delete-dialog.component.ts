import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDemandeMatriculeApp } from '../demande-matricule-app.model';
import { DemandeMatriculeAppService } from '../service/demande-matricule-app.service';

@Component({
  templateUrl: './demande-matricule-app-delete-dialog.component.html',
})
export class DemandeMatriculeAppDeleteDialogComponent {
  demandeMatriculeApp?: IDemandeMatriculeApp;

  constructor(protected demandeMatriculeAppService: DemandeMatriculeAppService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.demandeMatriculeAppService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
