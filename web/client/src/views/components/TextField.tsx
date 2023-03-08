import React from 'react';

export const TextField: React.FC<Props> = (props) => (
    <div className={props.className}>
        <label>{props.label}</label>
        <input type={props.inputType || 'text'} value={props.value} onChange={e => props.onChange(e.target.value) } />
        {props.error && <div style={{ color: 'red' }}>{props.error}</div>}
    </div>
);

interface Props {
    className?: string;
    label: string;
    inputType?: string;
    value: string;
    onChange: (value: string) => void;
    error?: string|null;
}
