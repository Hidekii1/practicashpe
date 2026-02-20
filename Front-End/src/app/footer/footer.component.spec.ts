import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule }               from '@angular/router/testing';
import { FooterComponent }                   from './footer.component';
import { AppModule }                         from '../app.module';
import { CUSTOM_ELEMENTS_SCHEMA }            from '@angular/core';

describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;

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
    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
