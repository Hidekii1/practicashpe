import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule }               from '@angular/router/testing';
import { UsersComponent }                    from './users.component';
import { AppModule }                         from '../app.module';
import { CUSTOM_ELEMENTS_SCHEMA }            from '@angular/core';

describe('UsersComponent', () => {
  let component: UsersComponent;
  let fixture: ComponentFixture<UsersComponent>;

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
    fixture = TestBed.createComponent(UsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
