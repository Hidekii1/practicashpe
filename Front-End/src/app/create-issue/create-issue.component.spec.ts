import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule }               from '@angular/router/testing';
import { CreateIssueComponent }              from './create-issue.component';
import { AppModule }                         from '../app.module';
import { CUSTOM_ELEMENTS_SCHEMA }            from '@angular/core';

describe('CreateIssueComponent', () => {
  let component: CreateIssueComponent;
  let fixture: ComponentFixture<CreateIssueComponent>;

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
    fixture = TestBed.createComponent(CreateIssueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
