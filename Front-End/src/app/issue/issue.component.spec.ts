import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule }               from '@angular/router/testing';
import { IssueComponent }                    from './issue.component';
import { AppModule }                         from '../app.module';
import { CUSTOM_ELEMENTS_SCHEMA }            from '@angular/core';

describe('IssueComponent', () => {
  let component: IssueComponent;
  let fixture: ComponentFixture<IssueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        AppModule
      ],
      schemas: [
        CUSTOM_ELEMENTS_SCHEMA
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
