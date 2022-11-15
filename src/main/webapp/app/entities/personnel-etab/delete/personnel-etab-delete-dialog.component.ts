import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonnelEtab } from '../personnel-etab.model';
import { PersonnelEtabService } from '../service/personnel-etab.service';

@Component({
  templateUrl: './personnel-etab-delete-dialog.component.html',
})
export class PersonnelEtabDeleteDialogComponent {
  personnelEtab?: IPersonnelEtab;

  constructor(protected personnelEtabService: PersonnelEtabService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.personnelEtabService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
